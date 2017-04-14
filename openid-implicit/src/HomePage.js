import React, {Component} from "react";
import {Redirect} from "react-router-dom";
import Header from "./Header";

let idToken = new URLSearchParams(location.hash).get("id_token");
const HomePage = ({match, location}) => (
    <div>
        <Header title="Home"/>
        <br/><br/>
        <div className="text-center">
            <a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=262806823822-va4iok5mm0f0qhh44j217t6ni4adrpqh.apps.googleusercontent.com&state=s2sffsd3eteterfb2434f&response_type=id_token%20token&scope=openid%20email%20profile&redirect_uri=http%3A%2F%2Flocalhost%3A3000&nonce=n-0S6_WzA2Mj"
               className="btn btn-danger">Login with Google</a>
            if({idToken} != null) {
            <Redirect to="/mortgage"/>
        }
        </div>
    </div>
);

export default HomePage;