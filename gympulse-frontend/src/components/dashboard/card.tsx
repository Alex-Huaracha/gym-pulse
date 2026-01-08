import type { ClassValue } from 'clsx';
import clsx from 'clsx';
import type { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';

function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

interface CardProps {
  children: ReactNode;
  className?: string;
  title?: string;
}

export const Card = ({ children, className, title }: CardProps) => {
  return (
    <div
      className={cn(
        'bg-[#1E1F20] border border-[#444746] rounded-2xl p-5',
        'flex flex-col',
        'transition-colors duration-200 hover:bg-[#2D2E2F]',
        className
      )}
    >
      {title && (
        <h3 className="text-[#8E918F] text-xs font-bold uppercase tracking-wider mb-4">
          {title}
        </h3>
      )}
      <div className="flex-1 overflow-hidden relative">{children}</div>
    </div>
  );
};
