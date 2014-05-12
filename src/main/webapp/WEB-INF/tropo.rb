#
# accept message and send to platybox api, response in api.
# 

require 'net/http'

#if this is not a checkin then send the message requested to send
unless $currentCall

call $numberToDial, { :network => "SMS"}
say $msg

else

if $currentCall.initialText

platybox_token = 'cb5408427d90b90c23a69dab73db1c71'
message = URI.encode($currentCall.initialText)
#escape tropo's error parsing the at sign in an email
if message.include?('%00')
  message['%00'] = '%40'
end

res = Net::HTTP.post_form(URI.parse('http://api.platybox.com/1/sms/checkin'),{'token'=>platybox_token, 'phone'=>$currentCall.callerID,'message'=>message}) 

response = res.body

if response
  say response
end

end

end
