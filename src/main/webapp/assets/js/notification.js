// 通知を非表示にするタイマーを管理する変数
var notificationTimer = null;

// 画面上部に表示されるバナー通知を送る関数
function sendBannerNotification() {

    // 通知として送るデータを作成する
    var data = {
        // 通知の種類
        // banner は画面上部通知を表す
        notifyType: "banner",

        // 通知の種類
        // appOpen は「アプリが開かれました」通知を表す
        notificationType: "appOpen",

        // 通知のタイトル
        title: "つながる～むを開きました！",

        // バナーをクリックしたときに遷移するURL
        targetUrl: "/connect-room/FamilyHomeServlet?sendKind=title",

        // 通知を送った時刻
        // 同じ内容の通知でも毎回反応させるために使用する
        time: new Date().getTime()
    };

    // localStorage に通知データを保存する
    localStorage.setItem("notification", JSON.stringify(data));
}


// 動画が届いたときのバナー通知を送る関数
function sendVideoNotification(profileId) {

    var data = {
        notifyType: "banner",

        // 動画通知を表す
        notificationType: "video",

        title: "動画が届きました！<br>早速見てみましょう",

        time: new Date().getTime()
    };

    // 高齢者が動画を送った場合 → 家族側へ通知
    if (profileId == 1) {
        data.targetUrl = "/connect-room/familyTitle.jsp";
    }

    // 家族が動画を送った場合 → 高齢者側へ通知
    else if (profileId == 2) {
        data.targetUrl = "/connect-room/seniorTitle.jsp";
    }

    localStorage.setItem("notification", JSON.stringify(data));
}


// localStorage の内容が変更されたときに実行される処理
window.addEventListener("storage", function(event) {

    // 変更されたデータのキーが notification でなければ処理しない
    if (event.key !== "notification") {
        return;
    }

    // localStorage に保存された文字列データをJavaScriptのオブジェクトに戻す
    var data = JSON.parse(event.newValue);

    // 通知の種類が banner の場合は、画面上部バナー通知を表示する
    if (data.notifyType === "banner") {
        showBannerNotification(data);
    }
});


// 画面上部バナー通知を表示する関数
function showBannerNotification(data) {

    // JSP内の id="notificationBanner" の要素を取得する
    var banner = document.getElementById("notificationBanner");

    // notificationBanner が存在しない画面では処理しない
    if (!banner) {
        return;
    }

    //    // アプリ起動通知だけ10分クールタイムを設定する
    //    if (data.notificationType === "appOpen") {
    //
    //        // 前回のアプリ起動通知表示時刻を取得
    //        var lastNotifyTime = localStorage.getItem("lastAppOpenNotifyTime");
    //
    //        // 現在時刻
    //        var now = new Date().getTime();
    //
    //        // 10分
    //        var tenMinutes = 10 * 60 * 1000;
    //
    //        // 10分以内なら通知しない
    //        if (lastNotifyTime &&
    //            (now - Number(lastNotifyTime) < tenMinutes)) {
    //            return;
    //        }
    //
    //        // アプリ起動通知の表示時刻を保存
    //        localStorage.setItem("lastAppOpenNotifyTime", now);
    //    }

    // 前の通知の非表示タイマーが残っている場合は消す
    if (notificationTimer != null) {
        clearTimeout(notificationTimer);
        notificationTimer = null;
    }

    // 前の通知内容を、新しい通知内容で上書きする
    banner.innerHTML = data.title;

    // バナーをクリックできる見た目にする
    banner.style.cursor = "pointer";

    // バナーをクリックしたときの処理
    banner.onclick = function() {
        if (data.targetUrl) {
            location.href = data.targetUrl;
        }
    };

    // バナー表示
    banner.classList.add("show");

    // 30秒後に非表示
    notificationTimer = setTimeout(function() {
        banner.classList.remove("show");
        notificationTimer = null;
    }, 30000);
}