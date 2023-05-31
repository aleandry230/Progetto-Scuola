const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();
const mysql = require("mysql");

const PORT = 3001;

const db = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "",
  database: "appscuola",
});

app.use(cors());
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.listen(PORT, () => {
  console.log("Eseguito sulla porta " + PORT);
});

app.get("/api/getVoti", (req, res) => {
  const sqlSelect = "SELECT * FROM voti";
  db.query(sqlSelect, (err, result) => {
    res.send(result);
    console.log(err);
  });
});
