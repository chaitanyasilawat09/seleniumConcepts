import React from 'react';

interface StatusBarProps {
  status: 'ready' | 'recording' | 'playing' | 'error';
  message: string;
}

const StatusBar: React.FC<StatusBarProps> = ({ status, message }) => {
  const getStatusColor = () => {
    switch (status) {
      case 'ready':
        return '#22c55e';
      case 'recording':
        return '#ef4444';
      case 'playing':
        return '#3b82f6';
      case 'error':
        return '#ef4444';
      default:
        return '#22c55e';
    }
  };

  return (
    <div
      style={{
        padding: '8px 12px',
        background: '#1e1e2e',
        borderTop: '1px solid #45475a',
        display: 'flex',
        alignItems: 'center',
        gap: '8px',
        fontSize: '12px'
      }}
    >
      <div
        style={{
          width: '8px',
          height: '8px',
          borderRadius: '50%',
          background: getStatusColor(),
          animation: status === 'recording' || status === 'playing' ? 'pulse 1s infinite' : 'none'
        }}
      />
      <span style={{ color: '#a6adc8' }}>{message}</span>
      <style>{`
        @keyframes pulse {
          0%, 100% { opacity: 1; }
          50% { opacity: 0.5; }
        }
      `}</style>
    </div>
  );
};

export default StatusBar;
