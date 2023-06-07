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
import Admin from './pages/Admin';
import AdminLogin from './pages/AdminLogin';
import { AuthContext } from './context/authContext';
import Leftbar from './components/LeftBar';

function App() {

  const { currentAccount, admin } = useContext(AuthContext);

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

  const AdminLayout = () =>{
    return(
      <div>
        <Navbar/>
        <div style={{display: "flex"}}>
          <Outlet/>
          </div>
      </div>
    )
  }

  const ProtectedRoute = ({ children }) => {
    console.log(currentAccount);
    if (!currentAccount) {
      return <Navigate to="/login" />;
    }
    return children;
  };

  const AdminRoute = ({ children }) =>{
    console.log(admin)
    if (!admin){
      return <Navigate to="/login/"/>;
    }
    return children
  }

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
          path: "/account/:id/addperson",
          element: <AddPerson/>
        },{
          path: "/account/:id/addrequest",
          element: <Request/>
        }
      ]
    },
    {
      path: "/admin/",
      element: (
        <AdminRoute>
          <AdminLayout/>
        </AdminRoute>
      ),
      children:[
        {
          path: "/admin/",
          element: <Admin/>
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
    },
    {
      path: "/login/admin",
      element: <AdminLogin/>
    }
  ])

  
  return (
    <>
      <RouterProvider router={router}/>
    </>
  )
}

export default App
