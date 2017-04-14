import React from "react";
import HomePage from "./HomePage";
import MortgagePage from "./MortgagePage";
import BrowserRouter from "react-router-dom/BrowserRouter";
import Route from "react-router-dom/Route";

const App = () => (
    <BrowserRouter>
        <div>
            <Route exact path="/" component={ HomePage }/>
            <Route path="/mortgage" component={ MortgagePage }/>
        </div>
    </BrowserRouter>
);

export default App;

