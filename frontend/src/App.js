import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ParseVacanciesPage from './ParseVacanciesPage';
import LocalVacanciesPage from './LocalVacanciesPage';

const App = () => {
  return (
    <Router className>
      <div className='contact__item'>
        <nav>
          <ul>
            <div style={{display:'flex'}}>
              <div className='padding__item'>
                <li>
                  <Link to="/parse" className='btn_big'>Парсинг вакансий</Link>
                </li>
              </div>
              <div className='padding__item'>
                <li>
                  <Link to="/local" className='btn_big'>Локальные вакансии из базы данных</Link>
                </li>
              </div>
            </div>
          </ul>
        </nav>

        <Routes>
        <Route path="/parse" element={<ParseVacanciesPage />} />
        <Route path="/local" element={<LocalVacanciesPage />} />
        </Routes>
      </div>
    </Router>
);
};

export default App;