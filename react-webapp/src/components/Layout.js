import React from 'react';
import Login from './Login';
import Form from './Form';
import Listado from './Listado';
import axios from 'axios';
import Mensajeria from './Mensajeria';
import Navbar from './Navbar';

class Layout extends React.Component {

    //constructor
    constructor() {
        super();

        //obtiene el token desde el localStorage
        let token = localStorage.getItem('Access-Token');

        this.state = {
            mensaje: null,
            productos: [],
            logged: token != null
        };
    }

    login = event => {

        event.preventDefault();
        
        const _this = this;
        axios.post(
            `http://localhost:8081/app-ws-rest-server/api/auth?username=${event.target.elements.username.value}&password=${event.target.elements.password.value}`,
        ).then(res => {
           
            localStorage.setItem('Access-Token', res.headers['access-token']);
            
            //cargo los tipo de productos
            axios.get(`http://localhost:8081/app-ws-rest-server/api/tipoproducto`)
                .then(res => {
                    debugger;
                    const tipoProductos = res.data;
                    localStorage.setItem('tipoProductos', JSON.stringify(tipoProductos));

                    this.findAllProductos();
                    //limpiar mensajeria: borrar el alerta
                    this.setState({
                        mensaje: null
                    });
                }
            );
        }).catch(function (error) {
            _this.setState({
                mensaje: "Usuario/Password inválido"
            });
        });
    }

    findProductos = async (e) => {
        
        e.preventDefault();

        let api_call = `http://localhost:8081/app-ws-rest-server/api/producto`;

        axios.get(api_call)
            .then(res => {
                const productos = res.data;
                
                this.setState(
                    {
                        productos: productos
                    }
                );
                console.log('done');
            }
        );
    }

    findAllProductos() {

        let api_call = `http://localhost:8081/app-ws-rest-server/api/producto`;

        axios.get(api_call)
            .then(res => {
                const productos = res.data;
                this.setState(
                    {
                        logged: true,
                        productos: productos
                    }
                );
            }
        );
    }


    createProducto = async (e) => {

        e.preventDefault();
        let jsonObject = {
            codigo: e.target.elements.codigo.value,
            titulo: e.target.elements.nombre.value,
            precio: e.target.elements.precio.value,
            tipoProducto: {
                id: e.target.elements.tipoProducto.value
            }
        }
        
        const _this = this;
        axios.post(
            'http://localhost:8081/app-ws-rest-server/api/producto',
            jsonObject,
            {
                headers: {
                    Authorization: 'Basic ' + localStorage.getItem('Access-Token'),
                }
            }
        ).then(res => {
            this.findAllProductos();
        }).catch(function (error) {
            // alert("No se ha podido crear el producto");
            _this.setState(
                {
                    mensaje: "No se ha podido dar de alta el producto"
                }
            );
        });
    }

    logout = async (e) => {
        e.preventDefault();

        //elimina el access token guardado en el localstorage
        localStorage.removeItem('Access-Token');
        localStorage.removeItem('tipoProductos');
        localStorage.removeItem('username');
        this.setState(
            {
                logged: false,
                productos: []
            }
        );
    }

    render() {

        return(
            <div className="container-fluid">
                {
                    this.state.mensaje!=null && 
                        <Mensajeria
                            mensaje={this.state.mensaje}
                        ></Mensajeria>
                }
                {
                    !this.state.logged && 
                        <Login 
                        login={this.login}
                        ></Login>
                }
                {
                    this.state.logged &&
                    <Navbar
                        username={this.username}
                        logout={this.logout}>
                    </Navbar>
                }
                {
                    this.state.logged &&
                        <Form
                         createProducto={this.createProducto}
                         productos={this.state.productos}
                        ></Form>
                }
                {
                    this.state.logged &&
                        <Listado
                            productos= {this.state.productos}
                        ></Listado>
                }
            </div>
        );
    }

    //life cile hook
    componentDidMount() {
        if(this.state.logged) {
            this.findAllProductos();
        }
    }
}

export default Layout;