import React from "react";
import LogoutPage from "./LogoutPage";

class Header extends React.Component {

    render() {
        const isHomePage = (this.props.title === "Home");
        if (isHomePage) {
            return (
                <header>
                    <h1>{this.props.title}</h1>
                </header>
            )
        } else {
            return (
                <header>
                    <h1>{this.props.title} <LogoutPage/></h1>
                </header>
            )
        }

    }
}

export default Header;