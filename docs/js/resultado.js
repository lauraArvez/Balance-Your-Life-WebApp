// Valida si el valor está entre 0 y 10
function esValorValido(valor) {
  return typeof valor === "number" && !isNaN(valor) && valor >= 0 && valor <= 10;
}

// Construye el DTO desde localStorage
function construirBalanceRequestDTO() {
  const activoNombres = ["Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"];
  const pasivoNombres = ["Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"];

  const activoRaw = JSON.parse(localStorage.getItem("activo") || "{}");
  const pasivoRaw = JSON.parse(localStorage.getItem("pasivo") || "{}");

  const activo = activoNombres.map(nombre => ({
    nombre,
    valor: isNaN(parseInt(activoRaw[nombre])) ? 0 : parseInt(activoRaw[nombre])
  }));

  const pasivo = pasivoNombres.map(nombre => ({
    nombre,
    valor: isNaN(parseInt(pasivoRaw[nombre])) ? 0 : parseInt(pasivoRaw[nombre])
  }));

  return { activo, pasivo };
}


// Valida los datos antes de enviar
function validarDatosLocales(dto) {
  const errores = [];

  dto.activo.forEach(item => {
    if (!esValorValido(item.valor)) {
      errores.push(`Activo → ${item.nombre} debe estar entre 0 y 10`);
    }
  });

  dto.pasivo.forEach(item => {
    if (!esValorValido(item.valor)) {
      errores.push(`Pasivo → ${item.nombre} debe estar entre 0 y 10`);
    }
  });

  return errores;
}


// Muestra los resultados en pantalla
function mostrarResultadosEnPantalla(data) {
  const { totalActivo, totalPasivo, interpretacion } = data;
  const diferencia = totalActivo - totalPasivo;

  const inputActivo = document.getElementById("valorActivo");
  const inputPasivo = document.getElementById("valorPasivo");
  const inputDiferencia = document.getElementById("valorDiferencia");
  const interpretacionDiv = document.getElementById("interpretacionResultado");

  if (inputActivo) inputActivo.value = totalActivo;
  if (inputPasivo) inputPasivo.value = totalPasivo;
  if (inputDiferencia) inputDiferencia.value = diferencia;
  if (interpretacionDiv) {
    interpretacionDiv.innerHTML = `<p>${interpretacion}</p>`;
  }
}


// Enviar y mostrar resultado completo
async function enviarBalanceYMostrarResultado() {
  const dto = construirBalanceRequestDTO();
  const errores = validarDatosLocales(dto);

  if (errores.length > 0) {
    alert("Corrige los siguientes errores:\n\n" + errores.join("\n"));
    return;
  }

  try {
    //const res = await fetch("http://localhost:8080/api/v1/balance/interpretar", {
    const res = await fetch("https://balance-your-life-webapp.onrender.com/api/v1/balance/interpretar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dto)
    });

    if (!res.ok) {
      const errorJson = await res.json();
      const mensajes = Array.isArray(errorJson?.errors)
        ? errorJson.errors.map(e => `${e.field}: ${e.defaultMessage}`)
        : Object.entries(errorJson).map(([campo, msg]) => `${campo}: ${msg}`);

      alert("Errores del servidor:\n\n" + mensajes.join("\n"));
      return;
    }

    const data = await res.json();
    const resultado = data.data || data;

    console.log("Resultado interpretado:", resultado);
    mostrarResultadosEnPantalla(resultado);
    
  } catch (err) {
    console.error("Error al conectar con el backend:", err.message);
    alert("No se pudo conectar con el servidor.");
  }
}


// solo abre la página si están los datos introducidos
function datosCompletos(obj, esperados) {
  return esperados.every(nombre => {
    const valor = parseInt(obj[nombre]);
    return !isNaN(valor) && valor >= 0 && valor <= 10;
  });
}

// Ejecutar al cargar la página
document.addEventListener("DOMContentLoaded", () => {
  //enviarBalanceYMostrarResultado();
  const activo = JSON.parse(localStorage.getItem("activo") || "{}");
  const pasivo = JSON.parse(localStorage.getItem("pasivo") || "{}");

  const nombresActivo = ["Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"];
  const nombresPasivo = ["Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"];

  const activoValido = datosCompletos(activo, nombresActivo);
  const pasivoValido = datosCompletos(pasivo, nombresPasivo);

  if (!activoValido || !pasivoValido) {
    alert("⚠️ Asegúrate de completar todos los campos con valores entre 0 y 10.");
    // Redirige a la página adecuada según lo que falte
    if (!activoValido) {
      window.location.href = "activo.html";
    } else {
      window.location.href = "pasivo.html";
    }
    return;
  }

  enviarBalanceYMostrarResultado();
});

// Abrir ventana centrada para detalle
document.getElementById("btnMostrarDetalle").addEventListener("click", () => {
  const width = 500;
  const height = 500;
  const left = (screen.width - width) / 2;
  const top = (screen.height - height) / 2;

  window.open("detalle.html", "DetalleValores", `width=${width},height=${height},top=${top},left=${left}`);
});


function abrirPuntuaciones() {
  const opciones = "width=600,height=450,top=100,left=100,resizable=no,scrollbars=no,toolbar=no,location=no,status=no";
  window.open("detalle.html", "_blank", opciones);
}
