

$('#discontinued').change(function (){
	checkIntervalDate()
})

$('#introduced').change(function (){
	checkIntervalDate()
})

function checkIntervalDate(){
	if ($('#introduced').val()!= "" && $('#discontinued').val()!= ""){
		if (moment($('#discontinued').val()).isBefore($('#introduced').val())){
			$('#validButton').attr('disabled',true)
			$('#checkdate').css('display','block')
		} else {
			$('#validButton').attr('disabled',false)
			$('#checkdate').css('display','none')
		}
	}
}