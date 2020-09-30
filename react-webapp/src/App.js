import React from 'react';
import './App.css';
import Layout from './components/Layout';
import ProductoForm from './components/ProductoForm';

function App() {
/*class App extends React.Component{

  //gano la posibilidad de usar constructores
  constructor(){

    super();

    //state
    this.state = {
      titulo: "Clase8 - React App"
    }
  }

    mostrarMensaje = () => {
      alert("HOLA");
    }

    render(){
      return (
          <div className="container">
            <h2>{this.state.titulo}</h2>
            <button onClick={this.mostrarMensaje} className="btn btn-info">Saludar</button>
            <ProductoForm></ProductoForm>
            <Layout 
            appTitulo={this.state.titulo}>
            saludarPadre ={this.mostrarMensaje} 
            </Layout>
        </div>
      );
    }
}
export default App;*/

return (
  <div className = 'container -fluid'>
   <Layout></Layout>
  </div>
  );
}

export default App;