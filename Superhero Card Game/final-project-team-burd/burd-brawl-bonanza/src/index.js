import React from 'react';
import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import ReactDOM from 'react-dom/client';

import BattlePage from './pages/BattlePage';
import ErrorPage from "./pages/ErrorPage";
import LoginPage from './pages/LoginPage';
import SelectionPage from './pages/SelectionPage';
import GameOverPage from './pages/GameOverPage';
import VictoryPage from './pages/VictoryPage';
import RegisterPage from './pages/RegisterPage';
import MusicLayout from './pages/MusicLayout';
import BattleMusic from './pages/BattleMusic';
import VictoryMusic from './pages/VictoryMusic';
import LoseMusic from './pages/LoseMusic';

// Create a browser router for page navigation
const router = createBrowserRouter([
    {
        path: "/", // FIXME; '/login' not '/'?
        element: <MusicLayout><LoginPage /></MusicLayout>,
        errorElement: <ErrorPage />,
    },
    {
        path: "/register",
        element: <MusicLayout><RegisterPage /></MusicLayout>,
    },
    {
        path: "/battle",
        element: <BattleMusic><BattlePage /></BattleMusic>,
    },
    {
        path: "/selection",
        element: <MusicLayout><SelectionPage /></MusicLayout>,
    },
    {
        path: "/gameover",
        element: <LoseMusic><GameOverPage /></LoseMusic>,
    },
    {
        path: "/victory",
        element: <VictoryMusic><VictoryPage /></VictoryMusic>,
    },
]);

// Get a reference to the div with ID root
const el = document.getElementById('root');

// Tell react to take control of that element
const root = ReactDOM.createRoot(el);

// Render the screen (RouterProvider goes to the root route)
root.render(<RouterProvider router={router} />);