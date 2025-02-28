const $showMoreBtn = $(".showMoreBtn");
let page = 1;

$showMoreBtn.on("click", function () {
    page++;
    const href = $(".href").val();
    performLoading()
    $.ajax(`/movie-x/api/video/commentList?v=${href}&page=${page}`,{
        method: "GET",
        contentType: "application/json",
    })
        .done(function (data) {
            if (page >= data.lastPage) {
                $showMoreBtn.addClass("d-none");
            }

            const html = data.comments
                .map(
                    (item) => `
                <div class="anime__review__item">
                    <div class="anime__review__item__pic">
                        <img src="${item.userImage}" alt="avt" />
                    </div>
                    <div class="anime__review__item__text">
                        <h6>
                            ${item.createdBy} - <span>${item.timeAgo}</span>
                        </h6>
                        <p>${item.content}</p>
                    </div>
                </div>
            `
                )
                .join("\n");
            $(".review-container").append(html);
            unPerformLoading();
        })
        .fail(function (error) {
            unPerformLoading();
            showSomethingWrongMessage()
        });
});
