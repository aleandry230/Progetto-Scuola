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

app.post("/api/login", (req, res) => {
  const email = req.body.email;
  const password = req.body.password;

  const sqlinsert = "SELECT * FROM account WHERE email = ? AND password = ?";
  db.query(sqlinsert, [email, password], (err, result) => {
    if (err) {
      res.status(500).send(err);
    } else {
      if (result.length > 0) {
        req.session.isAuth = true;
        console.log(req.session);

        res.status(200).send({
          session_id: req.sessionID,
          sessio_duration: req.session.cookie,
          id: result[0].client_id,
          name: result[0].name,
          surname: result[0].surname,
          email: result[0].email,
          isAdmin: result[0].isAdmin,
          isManager: result[0].isManager,
        });
      } else {
        res.status(400).send("Account Inesistente");
      }
    }
  });
});
