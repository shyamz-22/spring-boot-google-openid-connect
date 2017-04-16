import React from "react";
import Header from "./Header";
import MortgageCalculator from "./MortgageCalculator";
import LoginPage from "./LoginPage";
import * as loginService from "./LoginService";

const MortgagePage = () => (
    loginService.getUser().loggedIn ? <div>
        <Header title="Mortgage Calculator"/>
        <MortgageCalculator principal="200000" years="30" rate="5"/>
    </div> : <LoginPage/>
);

export default MortgagePage;