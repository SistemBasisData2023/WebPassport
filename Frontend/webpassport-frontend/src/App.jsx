import { useContext, useState } from 'react'
import { createBrowserRouter, RouterProvider, Outlet, Navigate } from 'react-router-dom';
import './App.css'
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import Navbar from './components/Navbar';
import Account from './pages/Account';
import Request from './pages/Request';
import AddPerson from './pages/AddPerson';
import { AuthContext } from './context/authContext';
import Leftbar from './components/LeftBar';

function App() {

  const { currentAccount } = useContext(AuthContext);

  const Layout = ()=>{
    return(
      <div>
        <Navbar/>
        <div style={{display: "flex"}}>
          <Leftbar/>
          <Outlet/>
        </div>
      </div>
    );
  }

  const ProtectedRoute = ({ children }) => {
    console.log(currentAccount);
    if (!currentAccount) {
      return <Navigate to="/login" />;
    }
    return children;
  };

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <ProtectedRoute>
          <Layout /> 
        </ProtectedRoute>
      ),
      children:[
        {
          path: "/",
          element: <Home/>
        },
        {
          path: "/account/:id",
          element: <Account/>
        },
        {
          path: "/addperson",
          element: <AddPerson/>
        },{
          path: "/addrequest",
          element: <Request></Request>
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
    <>
      <RouterProvider router={router}/>
    </>
  )
}

export default App
