import React from "react";
import {Redirect} from "react-router-dom";
import LoginPage from "./LoginPage";
import * as loginService from "../components/LoginService";


class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {isLoggedIn: false};
    }

    componentDidMount() {
        let idToken = new URLSearchParams(location.hash).get("id_token");

        loginService.recordAuthenticationSuccess(idToken, this)
            .then(function (result) {
                result.setState({isLoggedIn: loginService.getUser().loggedIn});
            });
    }

    render() {
        const isLoggedIn = this.state.isLoggedIn;
        if (isLoggedIn) {
            return <Redirect to="/user"/>;
        }
        else {
            return (<LoginPage/>);
        }
    }
}

export default HomePage;