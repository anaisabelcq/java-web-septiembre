import React from 'react';

export class Mensajeria extends React.Component {

    render() {
        return (
            <div className="row alert alert-danger" role="alert">
                <div className="col-12">
                    {this.props.mensaje}
                </div>
            </div>            
        );
    }
}

export default Mensajeria;
