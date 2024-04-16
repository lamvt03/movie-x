const loadingContainer = document.querySelector(".loading-container");
const showMoreBtn = document.querySelector(".showMoreBtn");
let page = 1;
showMoreBtn.onclick = () => {
    page++;
    loadingContainer.classList.remove("invisible");
    const href = document.querySelector(".href").value;
    fetch('/movie-x/api/video/commentList?v=' + href + '&page=' + page, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => response.json())
        .then((data) => {
            if (page >= data.lastPage) {
                showMoreBtn.classList.add("d-none");
            }
            const html = data.comments
                .map(
                    (item) => `
                <div class="anime__review__item">
                    <div class="anime__review__item__pic">
                        <img src="/views/template/user/img/default-avt.jpg" alt="avt" />
                    </div>
                    <div class="anime__review__item__text">
                        <h6>
                            ${item.createdBy} - <span>${
                        item.timeAgo
                    }</span>
                        </h6>
                        <p>${item.content}</p>
                    </div>
                </div>
            `
                )
                .join("\n");
            const container = document.querySelector(".review-container");
            container.innerHTML += html;
            loadingContainer.classList.add("invisible");
        })
        .catch((error) => {
            console.error("Error:", error);
        });
};
