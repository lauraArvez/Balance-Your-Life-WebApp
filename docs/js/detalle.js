    document.addEventListener("DOMContentLoaded", async () => {
    const activo = JSON.parse(localStorage.getItem("activo") || "{}");
    const pasivo = JSON.parse(localStorage.getItem("pasivo") || "{}");

    const totalActivo = Object.values(activo).reduce((acc, val) => acc + val, 0);
    const totalPasivo = Object.values(pasivo).reduce((acc, val) => acc + val, 0);
    const diferencia = totalActivo - totalPasivo;

    // Mostrar listas
    document.getElementById("listaActivo").innerHTML =
        Object.entries(activo).map(([clave, valor]) => `<li>${clave}: ${valor}</li>`).join("");

    document.getElementById("listaPasivo").innerHTML =
        Object.entries(pasivo).map(([clave, valor]) => `<li>${clave}: ${valor}</li>`).join("");

    // Mostrar fecha
    const fecha = new Date().toLocaleDateString("es-ES", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    });
    document.getElementById("fecha").textContent = `Fecha: ${fecha}`;

    // Construir DTO
    const dto = {
        activo: Object.entries(activo).map(([nombre, valor]) => ({ nombre, valor })),
        pasivo: Object.entries(pasivo).map(([nombre, valor]) => ({ nombre, valor }))
    };

    // Obtener interpretación desde backend
    try {
        const res = await fetch("http://localhost:8080/api/v1/balance/interpretar", {
        //const res = await fetch("https://balance-your-life-webapp.onrender.com/api/v1/balance/interpretar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
        });

        const data = await res.json();
        const interpretacion = data.interpretacion || data.data?.interpretacion || "";

        crearBotonPDF({ activo, pasivo, totalActivo, totalPasivo, diferencia, interpretacion, fecha });
    } catch (error) {
        console.error("No se pudo obtener la interpretación:", error.message);
    }
    });


    function crearBotonPDF({ activo, pasivo, totalActivo, totalPasivo, diferencia, interpretacion, fecha }) {
    const btn = document.createElement("button");
    btn.textContent = "Descargar PDF";
    btn.classList.add("btn", "btn-naranja"); // CAMBIO: clase naranja
    btn.style.marginTop = "2rem";

    btn.addEventListener("click", async () => {
        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF();

        let y = 20;

        pdf.setFont("helvetica", "bold");
        pdf.setFontSize(16);
        pdf.text("Balance Existencial - Mis puntuaciones", 20, y);

        y += 10;
        pdf.setFont("helvetica", "normal");
        pdf.setFontSize(12);
        pdf.text(`Fecha: ${fecha}`, 20, y);

        y += 10;
        pdf.setFont("helvetica", "bold");
        pdf.text("Totales:", 20, y);

        y += 8;
        pdf.setFont("helvetica", "normal");
        pdf.text(`Total Activo: ${totalActivo}`, 20, y);
        y += 6;
        pdf.text(`Total Pasivo: ${totalPasivo}`, 20, y);
        y += 6;
        pdf.text(`Diferencia: ${diferencia}`, 20, y);

        y += 10;
        pdf.setFont("helvetica", "bold");
        pdf.text("Interpretación:", 20, y);

        y += 8;
        const textoLimpio = interpretacion.replace(/<br\s*\/?>/gi, "\n");
        const interpretacionLineas = pdf.splitTextToSize(textoLimpio, 170);
        pdf.setFont("helvetica", "normal");
        pdf.setFontSize(10); // texto normal de interpretación
        pdf.text(interpretacionLineas, 20, y);
        y += interpretacionLineas.length * 6;

        y += 10;
        pdf.setFont("helvetica", "bold");
        pdf.setFontSize(10);
        pdf.text("Detalle Activo:", 20, y);

        y += 8;
        const lineasActivo = pdf.splitTextToSize(
        Object.entries(activo).map(([k, v]) => `${k}: ${v}`).join(", "),
        170
        );
        pdf.setFont("helvetica", "normal");
        pdf.setFontSize(9); // CAMBIO: texto más pequeño
        pdf.text(lineasActivo, 20, y);
        y += lineasActivo.length * 6;

        y += 10;
        pdf.setFont("helvetica", "bold");
        pdf.setFontSize(10);
        pdf.text("Detalle Pasivo:", 20, y);

        y += 8;
        const lineasPasivo = pdf.splitTextToSize(
        Object.entries(pasivo).map(([k, v]) => `${k}: ${v}`).join(", "),
        170
        );
        pdf.setFont("helvetica", "normal");
        pdf.setFontSize(9); // CAMBIO: texto más pequeño
        pdf.text(lineasPasivo, 20, y);

        pdf.save("detalle_balance_existencial.pdf");
    });

    // Agregar al final del popup
    document.querySelector(".popup-contenido").appendChild(btn);
    }
