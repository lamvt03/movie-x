const postComment = () => {
    document.querySelector('.loading-container').classList.remove('invisible');
    const href = document.querySelector('.href').value;
    const content = document.querySelector('.cmtInp').value;
    fetch('/movie-x/api/video/' + href + '/comment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            content: content
        })
    })
        .then(resp => resp.json())
        .then(data => {
            if (page >= data.lastPage) {
                showMoreBtn.classList.add("d-none");
            }
            const html = data.comments
                .map(item => `
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
                ).join("\n");
            document.querySelector('.review-container').innerHTML = html;
            document.querySelector('.loading-container').classList.add('invisible');
        });
}

const sendCmtBtn = document.querySelector('#sendCmtBtn');
sendCmtBtn.onclick = postComment;
const cmtInp = document.querySelector('.cmtInp');
cmtInp.onkeydown = (e) => {
    if (event.key === "Enter") {
        console.log('pressed');
        postComment();
    }
}

