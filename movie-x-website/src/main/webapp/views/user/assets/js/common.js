const performLoading = () => {
    $(".loading-container").removeClass("invisible");
    $("body").addClass("overflow-hidden");
}

const unPerformLoading = () => {
    $(".loading-container").addClass("invisible");
    $("body").removeClass("overflow-hidden");
}
