import './App.css';
import Main from './main/Main';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginJoin from './user/LoginJoin'
import Write from './user/Write';
import Edit from './user/Edit';
import Delete from './user/Delete';
import MyContent from './user/MyContent';

function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path='/' element={<Main />} />
          <Route path='/login' element={<LoginJoin />} />
          <Route path='/write/:code' element={<Write />} />
          <Route path='/edit/:id' element={<Edit />} />
          <Route path='/delete/:id' element={<Delete />} />
          <Route path='/my' element={<MyContent />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
