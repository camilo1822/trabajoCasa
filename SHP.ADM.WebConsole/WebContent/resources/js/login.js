/**
 * 
 */
function handleLoginRequest(xhr, status, args) {
    if(args.validationFailed || !args.loggedIn) {
         jQuery('#formLogin').effect("shake", {times:2}, 100);
    }
    else {
        location.href = args.ruta;
    }
}

/**
 * Funcion para sacar de sesion al usuario en el cambio de contraseña
 * @param xhr
 * @param status
 * @param args
 */
function handleLoginRequestChangePass(xhr, status, args) {
    if(args.loggetOut) {
         location.href = args.ruta;
    }
}