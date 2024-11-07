import * as React from "react";
import './App.css'
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";

function App() {

  const router = createBrowserRouter([
    {
      path: "/",
      element: <AppContainer/>,
      children: []
    }
  ]);
}

export default App

function AppContainer(props) {
  console.log(props)
  return (
    <div>
    </div>
  )
}
