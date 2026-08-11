/**
 * 動画撮影画面用JavaScript
 */

// 開始ボタン取得
const startBtn = document.getElementById("startBtn");

// 終了ボタン取得
const stopBtn = document.getElementById("stopBtn");

// カメラ映像表示用videoタグ取得
const camera = document.getElementById("camera");

// 録画時間表示取得
const recordTime = document.getElementById("recordTime");

// 録画中マーク取得
const recordDot = document.querySelector(".record-dot");

// 録画処理用
let mediaRecorder;

// 録画データ格納用
let recordedChunks = [];

// カメラの映像・音声データ
let currentStream;

// 録画開始時間
let recordStartTime;

// タイマー処理用
let recordTimer;

// 画面を開いた時点でカメラを起動する
startCamera();

// カメラ起動処理
function startCamera() {

    navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true
    })
        .then(function(stream) {

            console.log("カメラ取得成功");

            camera.srcObject = stream;

            camera.onloadedmetadata = function() {
                camera.play();
            };

            currentStream = stream;

        })
        .catch(function(error) {

            console.log("カメラを起動できませんでした", error);

        });
}

// 開始ボタン押下時
startBtn.addEventListener("click", function() {

    console.log("開始ボタン押された");

    if (currentStream == null) {
        alert("カメラが起動していません");
        return;
    }

    console.log("録画開始直前");

    recordedChunks = [];

    mediaRecorder = new MediaRecorder(currentStream);

    mediaRecorder.ondataavailable = function(event) {

        if (event.data.size > 0) {
            recordedChunks.push(event.data);
        }
    };

    mediaRecorder.onstop = function() {

        const recordedBlob = new Blob(recordedChunks, {
            type: "video/webm"
        });

        const formData = new FormData();

        const profileId = document.getElementById("profileId").value;
        formData.append("profileId", profileId);

        formData.append("video", recordedBlob, "record.webm");

        fetch(contextPath + "/VideoRecordServlet", {
            method: "POST",
            body: formData
        })
            .then(function(response) {

                window.location.href =
                    contextPath + "/videoRecordComplete.jsp?profileId=" + profileId;

            })
            .catch(function(error) {

                console.log("動画送信に失敗しました", error);

            });
    };

    mediaRecorder.start();

    // 録画中マークを点滅開始
    recordDot.classList.add("recording");

    console.log("タイマー開始");

    // 録画時間カウント開始
    startRecordTimer();

    startBtn.classList.add("hidden");
    stopBtn.classList.remove("hidden");
});

// 終了ボタン押下時
stopBtn.addEventListener("click", function() {

    if (mediaRecorder != null && mediaRecorder.state !== "inactive") {
        mediaRecorder.stop();
    }

    // 録画時間カウント停止
    stopRecordTimer();

    // 録画中マークの点滅停止
    recordDot.classList.remove("recording");

    stopBtn.classList.add("hidden");
});

// 録画時間カウント開始
function startRecordTimer() {

    recordStartTime = Date.now();

    recordTime.textContent = "00:00:00";

    clearInterval(recordTimer);

    recordTimer = setInterval(function() {

        const elapsedSeconds =
            Math.floor((Date.now() - recordStartTime) / 1000);

        recordTime.textContent = formatRecordTime(elapsedSeconds);

    }, 1000);
}

// 録画時間カウント停止
function stopRecordTimer() {

    clearInterval(recordTimer);
}

// 秒数を 00:00:00 形式に変換
function formatRecordTime(totalSeconds) {

    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;

    return String(hours).padStart(2, "0") + ":" +
        String(minutes).padStart(2, "0") + ":" +
        String(seconds).padStart(2, "0");
}