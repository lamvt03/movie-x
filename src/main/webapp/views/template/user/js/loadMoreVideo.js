let page = 1;
const loadMoreBtn = document.querySelector('.load-more-video-btn');
loadMoreBtn.onclick = () => {
    page++;
    document.querySelector('.loading-container').classList.remove('invisible');
    fetch('/movie-x/api/video/list?page=' + page, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => response.json())
        .then((data) => {
            if (page >= data.maxPage) {
                loadMoreBtn.classList.add("d-none");
            }
            const html = data.videos
                .map(
                    (video) => `
                <div class="col-lg-4 col-md-6 col-sm-12">
                                <div class="product__item overflow-hidden">
                                    <a
                                            href="/movie-x/video/detail?v=${video.href}">
                                        <div style="background-image: url('${video.poster}')" class="product__item__pic set-bg"
                                             data-setbg="${video.poster}">
                                            <!-- <div class="ep">18 / 18</div> -->
                                            <div class="comment">
                                                <i class="fa-solid fa-heart"></i> ${video.likeQuantity}
                                            </div>
                                            <div class="view" style="margin-right: 50px">
                                                <i class="fa-solid fa-comment"></i> ${video.commentQuantity }
                                            </div>
                                            <div class="view">
                                                <i class="fa fa-eye"></i> ${video.views}
                                            </div>
                                        </div>
                                    </a>
                                    <div class="product__item__text">
                                        <ul class="d-flex align-items-center justify-content-between">
                                            <li>${video.category}</li>
                                            <p class="time-ago mb-0">${video.timeAgo}</p>
                                        </ul>
                                        <h5>
                                            <a href="#">${video.title}</a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
            `
                )
                .join("\n");
            const wrapper = document.querySelector(".video-wrapper");
            wrapper.innerHTML += html;
            document.querySelector('.loading-container').classList.add("invisible");
        })
        .catch((error) => {
            console.error("Error:", error);
        });
}