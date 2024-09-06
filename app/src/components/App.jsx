import { useState } from 'react'
import '../styles/App.css'

function App() {
    const [board, setBoard] = useState(Array(9).fill(null)) // 3x3 game board
    const [playerTurn, setPlayerTurn] = useState('S') // Player 'S' goes first

    const handleCellClick = (index) => {
        const newBoard = [...board];
        newBoard[index] = newBoard[index] ? null : playerTurn;
        setBoard(newBoard);
    }

    return (
        <div className="game">
            <h1>SOS Game</h1>
            <div className="player-select">
                {['S', 'O'].map((player) => (
                    <label key={player}>
                        <input
                            type="radio"
                            value={player}
                            checked={playerTurn === player}
                            onChange={() => setPlayerTurn(player)}
                        />
                        {player}
                    </label>
                ))}
            </div>
            <div className="board">
                {board.map((cell, index) => (
                    <div className="cell" key={index}>
                        <input
                            type="checkbox"
                            checked={!!cell}
                            onClick={() => handleCellClick(index)}
                        />
                    </div>
                ))}
            </div>
        </div>
    )
}

export default App