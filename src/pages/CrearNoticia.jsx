import { useState } from "react";
import "./CrearNoticia.css";

const categories = [
  "Deportes",
  "Política",
  "Tecnología",
  "Entretenimiento",
  "Ciencia",
];

function CrearNoticia() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [categoryIndex, setCategoryIndex] = useState(0);

  const handleSubmit = () => {
    const newsData = {
      title,
      content,
      category: categories[categoryIndex],
    };

    console.log("Noticia enviada:", newsData);
    alert("Noticia subida con éxito 🚀");
  };

  return (
    <div className="news-form">
      <h2>Subir Nueva Noticia</h2>

      {/* Input del Título */}
      <input
        type="text"
        placeholder="Título de la noticia"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      {/* Selector de Categoría */}
      <div className="category-selector">
        <select
          id="category-select"
          value={categoryIndex}
          onChange={(e) => setCategoryIndex(Number(e.target.value))}
        >
          {categories.map((category, index) => (
            <option key={index} value={index}>
              {category}
            </option>
          ))}
        </select>
      </div>

      {/* Input del Contenido */}
      <textarea
        placeholder="Escribe la noticia aquí..."
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />

      {/* Botón de envío alineado a la derecha */}
      <div className="button-container">
        <button onClick={handleSubmit}>Subir Noticia</button>
      </div>
    </div>
  );
}

export default CrearNoticia;
