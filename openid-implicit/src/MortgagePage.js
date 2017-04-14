import React, {Component} from "react";
import Header from "./Header";
import MortgageCalculator from "./MortgageCalculator";

const MortgagePage = () => (
    <div>
        <Header title="Mortgage Calculator"/>
        <MortgageCalculator principal="200000" years="30" rate="5"/>
    </div>
);

export default MortgagePage;