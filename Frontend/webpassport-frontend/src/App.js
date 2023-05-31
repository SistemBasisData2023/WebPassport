import React, {useContext} from "react";
import { BrowserRouter, Route, Routes, Navigate, createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';

import './styles.scss'
import Navbar from "./components/navbar/Navbar";
import Leftbar from "./components/leftbar/Leftbar";
import Rightbar from "./components/rightbar/Rightbar";
import Home from "./pages/home/Home"
import Account from "./pages/account/Account";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";

function App() {
  

  const Layout = ()=>{
    return(
      <div>
        <Navbar/>
        <div style={{display: "flex"}}>
          <Leftbar/>
          <Outlet/>
          <Rightbar/>
        </div>
      </div>
    );
  }

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
          <Layout />  
      ),
      children:[
        {
          path: "/",
          element: <Home/>
        },
        {
          path: "/account/:id",
          element: <Account/>
        }
        
      ]
    },
    {
      path: "/login",
      element: <Login/>
    },
    {
      path: "/register",
      element: <Register/>
    }
  ])


  return (
     <div>
      <RouterProvider router={router}/>
     </div>
  );
}

export default App;
