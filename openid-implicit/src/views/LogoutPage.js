import React from "react";
import {Redirect} from "react-router-dom";

class LogoutPage extends React.Component {

    constructor(props) {
        super(props);
        this.handleLogoutClick = this.handleLogoutClick.bind(this);
        this.state = {isLoggedOut: false};
    }

    handleLogoutClick() {
        window.sessionStorage.clear();
        this.setState({isLoggedOut: true});
    }

    render() {
        const isLoggedOut = this.state.isLoggedOut;
        if (isLoggedOut) {
            return <Redirect to="/"/>;
        }
        else {
            return (<a href="#" onClick={this.handleLogoutClick}>
                <span className="glyphicon glyphicon-log-out pull-right"/>
            </a>);
        }
    }
}

export default LogoutPage;