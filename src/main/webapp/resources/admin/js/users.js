$(function(){
	$(document).on('change', '.roles input[type=checkbox]', function(){
		$this = $(this);
		console.log($this.prop('checked'));
		if($this.attr('data-role') != 'ROLE_PORTAL_USER'){
			if($this.prop('checked')){
				$('[data-role=ROLE_ADMIN]').prop('checked', true);	
			}else{
				if($('.roles :checked').not("[data-role=ROLE_PORTAL_USER]")
									   .not("[data-role=ROLE_ADMIN]").length === 0){
					$('[data-role=ROLE_ADMIN]').prop('checked', false);	
				}
			}
		}
	});
});