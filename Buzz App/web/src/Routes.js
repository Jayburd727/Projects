import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './Home';
import App from './App';
import Profile from './Profile';

function Routes() {
  return (
    <Router>
        <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/app" component={App} />
        <Route exact path="/profile" component={Profile} />
      </Switch>
    </Router>
  );
}

export default Routes;