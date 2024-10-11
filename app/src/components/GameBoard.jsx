import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/App.css'; // Ensure this path is correct

const GameBoard = () => {
    const [board, setBoard] = useState([]);
    const [currentPlayer, setCurrentPlayer] = useState('');
    const [size, setSize] = useState(4); // Default board size set to 4
    const [inputSize, setInputSize] = useState(4); // Input field for board size set to 4
    const [gameMode, setGameMode] = useState('simple'); // Default game mode

    useEffect(() => {
        createGame(size, gameMode);
    }, []);

    const createGame = async (size, gameMode) => {
        try {
            await axios.post('/api/game/create', null, { params: { size, gameMode } });
            await fetchBoard();
            await fetchCurrentPlayer();
        } catch (error) {
            console.error('Error creating game:', error);
        }
    };

    const fetchBoard = async () => {
        try {
            const response = await axios.get('/api/game/board');
            setBoard(response.data);
        } catch (error) {
            console.error('Error fetching board:', error);
        }
    };

    const fetchCurrentPlayer = async () => {
        try {
            const response = await axios.get('/api/game/player');
            setCurrentPlayer(response.data);
        } catch (error) {
            console.error('Error fetching current player:', error);
        }
    };

    const makeMove = async (index) => {
        try {
            await axios.post('/api/game/move', null, { params: { index, gameMode } });
            await fetchBoard();
            await fetchCurrentPlayer();
        } catch (error) {
            console.error('Error making move:', error);
        }
    };

    const handleInputChange = (event) => {
        setInputSize(event.target.value);
    };

    const handleGameModeChange = (event) => {
        setGameMode(event.target.value);
    };

    const handleStartGame = () => {
        const newSize = parseInt(inputSize, 10);
        setSize(newSize);
        createGame(newSize, gameMode);
    };

    return (
        <div className="game">
            <h1>Current Player: {currentPlayer || 'Loading...'}</h1>
            <div className="game-mode-select">
                <label>
                    <input
                        type="radio"
                        value="simple"
                        checked={gameMode === 'simple'}
                        onChange={handleGameModeChange}
                    />
                    Simple Game
                </label>
                <label>
                    <input
                        type="radio"
                        value="general"
                        checked={gameMode === 'general'}
                        onChange={handleGameModeChange}
                    />
                    General Game
                </label>
            </div>
            <div className="player-select">
                <input
                    type="number"
                    value={inputSize}
                    onChange={handleInputChange}
                    min="1"
                />
                <button onClick={handleStartGame}>Start New Game</button>
            </div>
            <div className="board-wrapper">
                <div className="board" style={{gridTemplateColumns: `repeat(${size}, 50px)`}}>
                    {board.map((cell, index) => (
                        <div
                            key={index}
                            onClick={() => makeMove(index)}
                            className="cell"
                        >
                            {cell}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default GameBoard;