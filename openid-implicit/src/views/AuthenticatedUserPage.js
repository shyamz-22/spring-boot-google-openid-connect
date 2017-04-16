import React from "react";
import Header from "./Header";
import LoginPage from "./LoginPage";
import * as loginService from "../components/LoginService";

const idTokenPanel = {
    "overflowY": "scroll"
};

const Profile = (props) => (
    <div className="row">
        <div className="col-lg-12">
            <div className="card hovercard">
                <div className="cardheader"/>
                <div className="avatar">
                    <img alt="" src={props.picture}/>
                </div>
                <div className="info">
                    <div className="title">
                        <a href="#">{props.name}</a>
                    </div>
                    <div className="desc">{props.email}</div>
                    <div className="desc">User Id : {props.userId}</div>
                    <div className="desc">Preferred Language: {props.locale}</div>
                </div>
            </div>
        </div>
    </div>
);

const IdToken = (props) => (
    <div className="row">
        <div className="col-lg-12">
            <div className="panel panel-warning">
                <div className="panel-heading">
                    <h3 className="panel-title">
                        <span className="glyphicon glyphicon-qrcode pull-left"/>
                        &nbsp;&nbsp;Id Token</h3>
                </div>
                <div className="panel-body" style={idTokenPanel}>
                    <figure> {props.idToken}</figure>
                </div>
            </div>
        </div>
    </div>
);

class AuthenticatedUserPage extends React.Component {

    render() {
        let user = loginService.getUser();
        if (user.loggedIn) {
            return (<div>
                <Header title="Profile"/>
                <Profile
                    picture={user.validatedPayload.picture}
                    name={user.validatedPayload.name}
                    userId={user.validatedPayload.sub}
                    locale={user.validatedPayload.locale}
                    email={user.validatedPayload.email}
                />
                <IdToken idToken={user.idToken}/>
            </div>);
        } else {
            return <LoginPage/>
        }

    }
}

export default AuthenticatedUserPage;