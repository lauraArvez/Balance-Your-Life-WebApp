document.addEventListener("DOMContentLoaded", () => {
  
  // 🧹 Borrar datos si estamos empezando el test
  if (window.location.pathname.includes("activo.html")) {
    localStorage.removeItem("activo");
    localStorage.removeItem("pasivo");
  }

  
  const tipo = window.location.pathname.includes("pasivo") ? "pasivo" : "activo";
  const nombresEsperados = tipo === "activo"
    ? ["Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"]
    : ["Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"];

  const mapeoCampos = {
    amor: "Amor",
    familia: "Familia",
    realizacion: "Realización Personal",
    coherencia: "Coherencia Interna",
    confort: "Confort Material",
    capacidad: "Capacidad",
    madurez: "Madurez",
    esfuerzo: "Esfuerzo",
    suerte: "Suerte",
    ayuda: "Ayuda"
  };

  const inputs = document.querySelectorAll('input[type="number"]');
  const errorDiv = document.getElementById("mensajeError");

  // Cargar valores guardados
  const datosGuardados = JSON.parse(localStorage.getItem(tipo) || "{}");
  inputs.forEach(input => {
    const campo = input.name;
    const clave = mapeoCampos[campo];
    if (datosGuardados[clave] !== undefined) {
      input.value = datosGuardados[clave];
    }

    input.addEventListener("input", () => validarInput(input));
    input.addEventListener("blur", () => {
      if (!validarInput(input)) input.focus();
    });
  });

  // Validar individualmente
  function validarInput(input) {
    const valor = input.value.trim();
    const numero = parseInt(valor);
    const campo = input.name;
    const clave = mapeoCampos[campo];

    const esValido = valor !== "" && !isNaN(numero) && numero >= 0 && numero <= 10;

    if (!esValido) {
      mostrarError("Todos los campos deben tener un valor entre 0 y 10.");
      input.classList.add("input-error");
      input.value = "";
      return false;
    }

    input.classList.remove("input-error");
    ocultarError();

    const datos = JSON.parse(localStorage.getItem(tipo) || "{}");
    datos[clave] = numero;
    localStorage.setItem(tipo, JSON.stringify(datos));
    return true;
  }

  // Validar todo al hacer clic en "Ver resultado"
  const btnVerResultado = document.getElementById("btnVerResultado");
if (btnVerResultado) {
  btnVerResultado.addEventListener("click", () => {
    const tipo = window.location.pathname.includes("pasivo") ? "pasivo" : "activo";
    const otroTipo = tipo === "activo" ? "pasivo" : "activo";

    const esperado = tipo === "pasivo"
      ? ["Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"]
      : ["Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"];

    const esperadoOtro = otroTipo === "pasivo"
      ? ["Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"]
      : ["Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"];

    const datos = JSON.parse(localStorage.getItem(tipo) || "{}");
    const datosOtro = JSON.parse(localStorage.getItem(otroTipo) || "{}");

    const incompleto = esperado.some(nombre => {
      const valor = datos[nombre];
      return valor === undefined || isNaN(parseInt(valor)) || parseInt(valor) < 0 || parseInt(valor) > 10;
    });

    const otroIncompleto = esperadoOtro.some(nombre => {
      const valor = datosOtro[nombre];
      return valor === undefined || isNaN(parseInt(valor)) || parseInt(valor) < 0 || parseInt(valor) > 10;
    });

    if (incompleto) {
      mostrarError(`⚠️ Completa todos los campos del ${tipo.toUpperCase()} con valores válidos entre 0 y 10.`);
      return;
    }

    if (otroIncompleto) {
      mostrarError(`⚠️ Antes de continuar debes completar también los valores del ${otroTipo.toUpperCase()}.`);
      setTimeout(() => {
        window.location.href = `${otroTipo}.html`;
      }, 1800); // Le da tiempo a ver el mensaje antes de redirigir
      return;
    }

    // Todo OK → Ir al resultado
    ocultarError();
    window.location.href = "resultado.html";
  });
}


  function mostrarError(texto) {
    if (!errorDiv) return;
    errorDiv.textContent = texto;
    errorDiv.classList.remove("oculto");
    errorDiv.classList.add("visible");
  }

  function ocultarError() {
    if (!errorDiv) return;
    errorDiv.textContent = "";
    errorDiv.classList.add("oculto");
    errorDiv.classList.remove("visible");
  }
});


