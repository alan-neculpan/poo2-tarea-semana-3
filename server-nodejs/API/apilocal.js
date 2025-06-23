const express = require('express');
const app = express();
const port = 3000;
// Ruta principal que devuelve un objeto JSON
app.get('/', (req, res) => {
    const responseObject = {
        tipo_cambio: [
            { id: 1, fecha: formatearFecha(), moneda: 'USD', valor: 916.58 }
        ]
    };
    res.json(responseObject);
});
// Inicia el servidor
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});
function formatearFecha() {
    const fecha = new Date();
    const dia = ('0' + fecha.getDate()).slice(-2);
    const mes = ('0' + (fecha.getMonth() + 1)).slice(-2);
    const anio = fecha.getFullYear();
    return `${dia}-${mes}-${anio}`;
}