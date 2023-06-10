import { useContext, useState } from 'react'
import { createBrowserRouter, RouterProvider, Outlet, Navigate } from 'react-router-dom';
import './App.scss'

import Home from './pages/Home';
import Navbar from './components/Navbar';
import Account from './pages/Account';
import Request from './pages/Request';
import AddPerson from './pages/AddPerson';
import Admin from './pages/Admin';
import AdminLogin from './pages/AdminLogin';
import { AuthContext } from './context/authContext';
import Leftbar from './components/LeftBar';
import RequestDetails from './pages/RequestDetails';
import Welcome from './pages/Welcome';
import AdminRequestList from './pages/AdminRequestList';

function App() {

  const { currentAccount, admin } = useContext(AuthContext);
  const [isLeftBarOpen, setIsLeftBarOpen] = useState(true);

  const Layout = ()=>{
    return(
      <div>
        <Navbar/>
        <div className={`root `}>
          <div className={`left ${isLeftBarOpen ? 'open' : ''}`}>
            <div className='leftBar'>
              <Leftbar/>
            </div>
            <button className='toggleLeftbar' onClick={() => setIsLeftBarOpen(!isLeftBarOpen)}>
              {isLeftBarOpen ? ">": "<"}
            </button>
          </div>
          
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
      return <Navigate to="/welcome" />;
    }
    return children;
  };

  const AdminRoute = ({ children }) =>{
    console.log(admin)
    if (!admin){
      return <Navigate to="/welcome/"/>;
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
          path: "/account/:id",
          element: <Home/>
        },
        {
          path: "/account/:id/info",
          element: <Account/>
        },
        {
          path: "/account/:id/addperson",
          element: <AddPerson/>
        },{
          path: "/account/:id/addrequest",
          element: <Request/>
        },{
          path: "/request/:id",
          element: <RequestDetails/>
        }
      ]
    },
    {
      path: "/admin/:id",
      element: (
        <AdminRoute>
          <AdminLayout/>
        </AdminRoute>
      ),
      children:[
        {
          path: "/admin/:id",
          element: <Admin/>
        },{
          path: "/admin/:id/request",
          element: <AdminRequestList/>
        }
      ]
    },
    {
      path: "/welcome",
      element: <Welcome/>
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
