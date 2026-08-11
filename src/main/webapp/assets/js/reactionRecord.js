'use strict';
console.log('reactionRecord.js 読み込み成功');
window.addEventListener('DOMContentLoaded', function() {

    const videoPlayer = document.getElementById('videoPlayer');
    const playButton = document.getElementById('playButton');
    const messageBottom = document.getElementById('messageBottom');
    const videoTime = document.getElementById('videoTime');
    const albumButton = document.getElementById('albumButton');
    const messageTitle = document.getElementById('messageTitle');

    let isRecording = false;
    let mediaRecorder = null;
    let recordedChunks = [];
    let reactionStream = null;

    // 初期表示制御
    if (profileId == 1) {

        if (!isAlbumPlay) {
            // 高齢者側 ＆ 未読動画

            if (messageTitle != null) {
                messageTitle.style.display = 'block';
            }

            // 未リアクションの場合だけ下の文章を表示
            if (reactionCount == 0) {
                messageBottom.innerHTML =
                    '見ているようすを動画にして<br>ご家族へお届けします';
            } else {
                messageBottom.innerHTML = '';
            }

            // 未読動画は、再生が終わるまでアルバムへボタンを非表示
            if (albumButton != null) {
                albumButton.style.display = 'none';
            }

        } else {
            // 高齢者側 ＆ アルバムから再生した既読動画

            if (messageTitle != null) {
                messageTitle.style.display = 'none';
            }

            messageBottom.innerHTML = '';

            if (albumButton != null) {
                albumButton.style.display = 'block';
            }
        }

    } else {
        // 家族側

        if (messageTitle != null) {
            messageTitle.style.display = 'block';
        }

        messageBottom.innerHTML = '';

        if (albumButton != null) {
            albumButton.style.display = 'block';
        }
    }


    playButton.addEventListener('click', function() {



        // 高齢者側 かつ 本日の反応動画がまだない場合だけ録画開始
        if (profileId == 1 && reactionCount == 0 && !isAlbumPlay) {

            messageBottom.innerHTML =
                '見ているようすを動画にして<br>ご家族へお届けします';

            if (albumButton != null) {
                albumButton.style.display = 'none';
            }

            startReactionRecord();

        } else {
            videoPlayer.play();
        }
    });

    videoPlayer.addEventListener('play', function() {
        playButton.style.display = 'none';
        if (profileId == 1 && reactionCount == 0 && !isAlbumPlay) {
            // 動画再生中のメッセージ
            messageBottom.innerHTML = '見ているようすを<br>お届けしています...';
        }
    });

    videoPlayer.addEventListener('ended', function() {

        console.log('ended');
        console.log(profileId);
        console.log(reactionCount)
        playButton.style.display = 'block';

        // 録画中なら、動画終了と同時に録画停止
        if (isRecording) {
            stopReactionRecord();
        }

        if (profileId == 1) {

            // 未読動画の場合だけ、終了後にアルバムへボタンを表示
            if (!isAlbumPlay) {

                // 未リアクションだった場合だけ完了メッセージを表示
                if (reactionCount == 0) {
                    messageBottom.innerHTML = 'ようすをお届けしました';
                } else {
                    messageBottom.innerHTML = '';
                }

                if (albumButton != null) {
                    albumButton.style.display = 'block';
                }
            }

            // アルバム再生の場合は文章を出さない
            if (isAlbumPlay) {
                messageBottom.innerHTML = '';
            }
        }
    });

    videoPlayer.addEventListener('timeupdate', function() {
        videoTime.textContent =
            formatTime(videoPlayer.currentTime) + '/' +
            formatTime(videoPlayer.duration);
    });

    function startReactionRecord() {

        if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
            messageBottom.innerHTML =
                'カメラ機能が使用できません。localhostで開いているか確認してください。';
            return;
        }

        navigator.mediaDevices.getUserMedia({
            video: true,
            audio: true
        })
            .then(function(stream) {
                //console.log(profileId);
                console.log('カメラ取得成功');

                reactionStream = stream;
                recordedChunks = [];

                mediaRecorder = new MediaRecorder(reactionStream);

                mediaRecorder.ondataavailable = function(event) {
                    if (event.data.size > 0) {
                        recordedChunks.push(event.data);
                    }
                };

                mediaRecorder.onstop = function() {

                    if (recordedChunks.length === 0) {
                        messageBottom.innerHTML = '反応動画データが取得できませんでした。';
                        return;
                    }

                    const recordedBlob = new Blob(recordedChunks, {
                        type: 'video/webm'
                    });

                    const formData = new FormData();

                    formData.append('video', recordedBlob, 'reaction.webm');

                    fetch(contextPath + '/ReactionRecordServlet', {
                        method: 'POST',
                        body: formData
                    })
                        .then(function(response) {

                            if (response.ok) {
                                sendVideoNotification(1);
                                messageBottom.innerHTML = 'ようすを<br>お届けしました';

                                if (albumButton != null) {
                                    albumButton.style.display = 'block';
                                    albumButton.style.visibility = 'visible';
                                }

                                isRecording = false;

                            } else {
                                messageBottom.innerHTML = '反応動画の送信に失敗しました。';
                            }
                        })
                        .catch(function(error) {
                            console.log('反応動画送信に失敗しました', error);
                            messageBottom.innerHTML = '反応動画の送信に失敗しました。';
                        });
                };

                mediaRecorder.start();
                console.log('録画開始');

                isRecording = true;

                if (albumButton != null) {
                    albumButton.style.display = 'none';
                }

                videoPlayer.play();
            })
            .catch(function(error) {

                console.error('カメラ起動エラー:', error.name, error.message);

                if (error.name === 'NotAllowedError') {
                    messageBottom.innerHTML =
                        'カメラまたはマイクの使用が許可されていません。';
                } else if (error.name === 'NotFoundError') {
                    messageBottom.innerHTML =
                        'カメラまたはマイクが見つかりません。';
                } else if (error.name === 'NotReadableError') {
                    messageBottom.innerHTML =
                        'カメラまたはマイクが他のアプリで使用中です。';
                } else {
                    messageBottom.innerHTML =
                        'カメラを起動できませんでした。';
                }
            });
    }

    function stopReactionRecord() {
        console.log('録画停止処理開始');

        if (mediaRecorder == null) {
            return;
        }

        if (mediaRecorder.state === 'recording') {
            mediaRecorder.stop();
        }

        isRecording = false;

        messageBottom.innerHTML = '反応動画を送信しています...';

        if (reactionStream != null) {
            reactionStream.getTracks().forEach(function(track) {
                track.stop();
            });
        }
    }

    //    function sendReactionVideo() {
    //
    //        if (recordedChunks.length === 0) {
    //            messageBottom.innerHTML = '反応動画データが取得できませんでした。';
    //            return;
    //        }
    //
    //        const videoBlob = new Blob(recordedChunks, {
    //            type: 'video/webm'
    //        });
    //
    //        const formData = new FormData();
    //        formData.append('video', videoBlob, 'reaction.webm');
    //
    //        fetch(contextPath + '/ReactionRecordServlet', {
    //            method: 'POST',
    //            body: formData
    //        })
    //            .then(function(response) {
    //
    //                if (response.ok) {
    //
    //                    //反応動画を送信したあとにJSを実行し家族側に通知を出す
    //                    // sendVideoNotification(1);
    //
    //                    messageBottom.innerHTML = 'ようすを<br>お届けしました';
    //
    //                    if (albumButton != null) {
    //                        albumButton.style.display = 'block';
    //                    }
    //
    //                    // 再送信・再録画を防ぐ
    //                    isRecording = false;
    //
    //                    // 自動リダイレクトはしない
    //                    return;
    //
    //                } else {
    //                    messageBottom.innerHTML = '反応動画の送信に失敗しました。';
    //                }
    //            })
    //            .catch(function(error) {
    //                console.error('送信エラー:', error);
    //                messageBottom.innerHTML = '反応動画の送信に失敗しました。';
    //            });
    //    }

    function formatTime(seconds) {

        if (isNaN(seconds)) {
            return '0:00';
        }

        const min = Math.floor(seconds / 60);
        const sec = Math.floor(seconds % 60);

        return min + ':' + String(sec).padStart(2, '0');
    }
});