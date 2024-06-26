const postComment = () => {
    document.querySelector('.loading-container').classList.remove('invisible');
    const href = document.querySelector('.href').value;
    const content = document.querySelector('.cmtInp').value;
    if(!content){
        return;
    }
    const config = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            content: content
        })
    }
    fetch(`/movie-x/api/video/${href}/comment`, config)
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                if(page < data.lastPage){
                    showMoreBtn.classList.remove('d-none');
                }
                const html = data.comments.map(item => `<div class="anime__review__item">
                                    <div class="anime__review__item__pic">                 
                                        <img src="${item.userImage}" alt="avt" />
                                    </div>
                                    <div class="anime__review__item__text">
                                        <h6>
                                            ${item.createdBy} - <span>${item.timeAgo}</span>
                                        </h6>
                                        <p>${item.content}</p>
                                    </div>
                                </div>`).join("\n");

                //append comments;
                document.querySelector('.review-container').innerHTML = html;

                //hidden loading
                document.querySelector('.loading-container').classList.add('invisible');

                //reset input
                document.querySelector('.cmtInp').value = '';
            })
}



const sendCmtBtn = document.querySelector('#sendCmtBtn');
sendCmtBtn.onclick = postComment;
const cmtInp = document.querySelector('.cmtInp');
cmtInp.onkeydown = (e) => {
    if (e.key == "Enter") {
        postComment();
    }
};

