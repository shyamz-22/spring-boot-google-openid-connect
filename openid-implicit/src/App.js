import React from "react";
import BrowserRouter from "react-router-dom/BrowserRouter";
import Route from "react-router-dom/Route";
import HomePage from "./views/HomePage";
import AuthenticatedPage from "./views/AuthenticatedUserPage";

const App = () => (
    <BrowserRouter>
        <div>
            <Route exact path="/" component={ HomePage }/>
            <Route path="/user" component={ AuthenticatedPage }/>
        </div>
    </BrowserRouter>
);

export default App;

