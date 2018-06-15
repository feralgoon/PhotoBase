 $('.imagedetail').slick({
  slidesToShow: 1,
  slidesToScroll: 1,
  arrows: false,
  fade: true,
  asNavFor: '.imagecarousel'
});
$('.imagecarousel').slick({
  slidesToShow: 1,
  slidesToScroll: 1,
  asNavFor: '.imagedetail',
  dots: true,
  centerMode: true,
  lazyLoad: 'ondemand',
  variableWidth: true,
  focusOnSelect: true
});