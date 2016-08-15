function justNumbers(e)
	{		
		var keynum = window.event ? window.event.keyCode : e.which;
			if ((keynum == 8) || (keynum == 47))
			return true;	 
		return /\d/.test(String.fromCharCode(keynum));
	}

function retornaSoloLetras(e)
		{
		
		var keynum = window.event ? window.event.keyCode : e.which;
		
		
			
		 if ((event.keyCode != 32) && (event.keyCode < 65) || (event.keyCode > 90) && (event.keyCode < 97) || (event.keyCode > 122))
		 { 
			return  false;
		 }else{
			return event.returnValue = true;
		 }
		}