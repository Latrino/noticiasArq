import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import Home from "./pages/Home";
import Noticia from "./pages/Noticia";
import CrearNoticia from "./pages/CrearNoticia";

import "./App.css";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/noticia" element={<Noticia />} />
          <Route path="/crearNoticia" element={<CrearNoticia />} />
        </Routes>
        <Navbar />
      </Router>
    </>
  );
}

export default App;
