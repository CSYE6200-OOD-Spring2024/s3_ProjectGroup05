
import { getSignOut } from '@/store/AuthStore'
import { DropdownMenuContent, DropdownMenu, DropdownMenuItem, DropdownMenuTrigger } from './ui/dropdown-menu'
import { UserCircle } from 'lucide-react'
import { Button } from './ui/button';


export default function User() {

  const signOut = getSignOut();

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant={'outline'} size='icon'>
          <UserCircle className='h-[1.2rem] w-[1.2rem]' />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuItem onClick={() => signOut()}>SignOut</DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
