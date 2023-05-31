import { useState, useEffect } from "react";
import "./App.css";
import Axios from "axios";

function App() {
  const [voti, setVoti] = useState([]);

  useEffect(() => {
    Axios.get("http://localhost:3001/api/getVoti")
      .then((response) => {
        setVoti(response.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <ul>
        {voti.length > 0 && (
          <div className="card-container">
            {voti.map((v) => (
              <li>
                {v.voto}, {v.materia}
              </li>
            ))}
          </div>
        )}
      </ul>
    </>
  );
}

export default App;
