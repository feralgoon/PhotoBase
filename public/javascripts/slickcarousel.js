$(document).ready(function(){
    $('.testcarousel').slick({
      infinite: true,
      arrows: true,
      dots: true,
      variableWidth: true,
      centerMode: true,
        centerPadding: '20px',
        slidesToShow: 3,
        responsive: [
          {
            breakpoint: 768,
            settings: {
              arrows: false,
              centerMode: true,
              centerPadding: '15px',
              slidesToShow: 3
            }
          },
          {
            breakpoint: 480,
            settings: {
              arrows: false,
              centerMode: true,
              centerPadding: '15px',
              slidesToShow: 1
            }
          }
        ]
    });
});