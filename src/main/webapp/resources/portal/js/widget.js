$(function(){
  // Get the parent page URL as it was passed in, for browsers that don't support
  // window.postMessage (this URL could be hard-coded).
  var parent_url = decodeURIComponent( document.location.hash.replace( /^#/, '' ) );
  

  function setHeight() {
    $.postMessage({ if_height: $('body').outerHeight( true ) }, parent_url, parent );
  };
  
  setHeight();
  
});
