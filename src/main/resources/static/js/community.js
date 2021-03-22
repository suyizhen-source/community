
// コメントする
function post() {
    let parentId = $("#question_id").val();
    let content = $("#comment_content").val();

    if (!content){
        alert("空欄のコメントをコミットすることができません。");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2000) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=115285b7216b4c2397a1&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
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
//二級コメントを展開する
function collapseComments(e){
    let id = e.getAttribute("data-id");
    let btn = $("#commentBtn-" + id);
    let comment = $("#comment-" + id);
    comment.toggleClass("in");
    btn.toggleClass("active");
}