import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <div className="navbar">
      <Link to="/" className="menu-text">
        <h1>Home</h1>
      </Link>
      <Link to="/noticia" className="menu-text">
        <h1>Noticia</h1>
      </Link>
      <Link to="/crearNoticia" className="menu-text">
        <h1>Crear</h1>
      </Link>
    </div>
  );
}

export default Navbar;
