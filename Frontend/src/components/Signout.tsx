
import { getSignOut } from '@/store/AuthStore';
import { Button } from './ui/button'

export default function Signout() {

  const signOut = getSignOut();

  return (
    <Button variant={'ghost'} onClick={signOut}>
      Sign Out
    </Button>
  )
}
