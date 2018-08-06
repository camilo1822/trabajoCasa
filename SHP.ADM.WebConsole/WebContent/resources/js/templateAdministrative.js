/**
 * 
 */
function handleLoginRequest(xhr, status, args) {
    if(args.loggetOut) {
         location.href = args.ruta;
    }
}
function justNumbers(e){
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
    return true;

    return /\d/.test(String.fromCharCode(keynum));
}
function movesClock(){ 
    momentoActual = new Date() 
    hora = momentoActual.getHours() 
    minuto = momentoActual.getMinutes() 
    segundo = momentoActual.getSeconds() 

    horaImprimible = hora + " : " + minuto + " : " + segundo 

    document.form_clock.reloj.value = horaImprimible 
    
    setTimeout("movesClock()",1000) 
}
function cleanInputDocumentNumberSearch(){
	document.getElementById("formSearchUser:documentNumber").value ='';
}