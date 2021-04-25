function commit(targetID, content, type) {
    if (!content) {
        alert("空欄のコメントをコミットすることができません。");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetID,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2000) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=115285b7216b4c2397a1&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message)
                }
                console.log(response);
            }
        },
        dataType: "json"
    });
}

// 回答する
function post() {
    let parentId = $("#question_id").val();
    let content = $("#comment_content").val();
    commit(parentId, content, 1);
}

// 回答をコメントする
function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    commit(commentId, content, 2);
}

//二級コメントを展開する
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let btn = $("#commentBtn-" + id);
    let comment = $("#comment-" + id);
    if (comment.children().length == 1) {
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                let subCommentContainer = $("#comment-" + id);
                let mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl
                }));

                let mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                let mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                let commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement)
            });
        });
    }
    comment.toggleClass("in");
    btn.toggleClass("active");
}

function showSelectTag() {
    $("#select-tag").show();
    $("#error").hide();
}

function hideSelectTag() {
    $("#select-tag").hide();
}

function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous) {
        if (previous.indexOf(value) == -1) {
            $("#tag").val(previous + ',' + value);
        }
    } else {
        $("#tag").val(value);
    }
}

function sendFile(files) {
    let data = new FormData();//存放上传的文件数据
    data.append("file", files);
    $.ajax({
        type: "POST",
        url: "/uploadImg",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            $('#summernote').summernote('insertImage', 'http://localhost:8080/images/' + data.fileName);
        },
        error: function (data) {
            alert(data.error);
        }
    });
}

function removeFile(target) {
    let imgSrc = target[0].currentSrc;
    let data = new FormData();
    data.append("imgSrc", imgSrc);
    $.ajax({
        data: data,
        type: "POST",
        url: "/delPathFile",
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            console.log(data.message);
        },
        error: function (data) {
            alert(data.error);
        }
    })
}

function thumbComments(e) {
    debugger
    let url = e.getAttribute("data-id");
    let id = e.getAttribute("Btn-id");
    let thumbBtn = $("#thumb-" + id);
    let tags = thumbBtn.children("#thumbChildElement");
    $.getJSON("/thumb/" + url, function (data) {
        if (data[0]) {
            thumbBtn.addClass("active");
            tags.html(data[1]);
        } else {
            thumbBtn.removeClass("active");
            tags.html(data[1]);
        }
    });
}